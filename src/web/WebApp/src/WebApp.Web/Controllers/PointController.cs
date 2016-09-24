using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using WebApp.Entity.Data;
using WebApp.Web.Services;

namespace WebApp.Web.Controllers
{
    public class PointController
    {
        private ApplicationDbContext context;

        public PointController(ApplicationDbContext context)
        {
            this.context = context;
        }

        public List<object> Index(float Latitude, float Longitude)
        {
            IDataProvider[] dataProviders = new IDataProvider[]
            {
                new SchoolProvider(this.context, "00004")
            };

            var results = new List<object>();

            foreach (var dataProvider in dataProviders)
            {
                results.Add(dataProvider.Get(Latitude, Longitude));
            }

            return results;
        }
    }
}
