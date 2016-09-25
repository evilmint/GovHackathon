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

        public Dictionary<string, object> Index(float Latitude, float Longitude)
        {
            IDataProvider[] dataProviders = new IDataProvider[]
            {
                new PartyabilityProvider(this.context),
                new SchoolProvider(this.context, "00004"),
                new SchoolProvider(this.context, "00001"),
                new SchoolProvider(this.context, "00003")
            };

            var results = new Dictionary<string, object>();

            foreach (var dataProvider in dataProviders)
            {
                results.Add(dataProvider.Name, dataProvider.Get(Latitude, Longitude));
            }

            return results;
        }
    }
}
