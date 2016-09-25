using Microsoft.AspNetCore.Identity.EntityFrameworkCore;
using Microsoft.EntityFrameworkCore;
using WebApp.Entity.Models;

namespace WebApp.Entity.Data
{
    public class ApplicationDbContext : IdentityDbContext<ApplicationUser>
    {
        public ApplicationDbContext(DbContextOptions<ApplicationDbContext> options)
            : base(options)
        {
        }

        public virtual DbSet<Event> Events { get; set; }
        public virtual DbSet<School> Schools { get; set; }
        public virtual DbSet<Stop> Stops { get; set; }
        public virtual DbSet<Relic> Relics { get; set; }
        public virtual DbSet<Partyability> Partyabilities { get; set; }
        public virtual DbSet<House> Houses { get; set; }
        public virtual DbSet<HouseSummary> HouseSummaries { get; set; }
        public virtual DbSet<Metric> Metrics { get; set; }
        public virtual DbSet<PointMetric> PointMetric { get; set; }
        public virtual DbSet<PointSummary> PointSummaries { get; set; }
        public virtual DbSet<Point> Points { get; set; }

        protected override void OnModelCreating(ModelBuilder builder)
        {
            base.OnModelCreating(builder);
            // Customize the ASP.NET Identity model and override the defaults if needed.
            // For example, you can rename the ASP.NET Identity table names and more.
            // Add your customizations after calling base.OnModelCreating(builder);
        }
    }
}