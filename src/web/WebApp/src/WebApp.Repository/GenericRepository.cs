using Microsoft.EntityFrameworkCore;
using System;
using System.Linq;
using System.Linq.Expressions;
using WebApp.Entity.Data;

namespace WebApp.Repository
{
    public abstract class GenericRepository<TContext, TEntity> : IGenericRepository<TEntity>
        where TEntity : class
        where TContext : ApplicationDbContext, new()
    {

        private TContext _entities = new TContext();

        public virtual TContext Context
        {
            get
            {
                return _entities;
            }
        }

        public virtual IQueryable<TEntity> GetAll()
        {
            return _entities.Set<TEntity>();
        }

        public IQueryable<TEntity> Find(Expression<Func<TEntity, bool>> predicate)
        {
            return _entities.Set<TEntity>()
                .Where(predicate);
        }

        public virtual void Add(TEntity entity)
        {
            _entities.Set<TEntity>()
                .Add(entity);
        }

        public virtual void Delete(TEntity entity)
        {
            _entities.Set<TEntity>()
                .Remove(entity);
        }

        public virtual void Edit(TEntity entity)
        {
            _entities.Entry(entity).State = EntityState.Modified;
        }

        public virtual void Save()
        {
            _entities.SaveChanges();
        }
    }
}
