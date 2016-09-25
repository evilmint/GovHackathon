using System;
using System.Collections.Generic;
using System.Linq;
using WebApp.Entity.Data;
using WebApp.Web.Services;

namespace WebApp.Web.Controllers
{
    internal class StopsProvider : IDataProvider
    {
        private ApplicationDbContext context;
        private StopType stopType;

        public StopsProvider(ApplicationDbContext context, StopType stopType)
        {
            this.context = context;
            this.stopType = stopType;
        }

        public string Name
        {
            get
            {
                switch (this.stopType)
                {
                    case StopType.Bus: return "busStop";
                    default: return "tramStop";
                }
            }
        }

        public Dictionary<string, object> Get(double latitude, double longitude)
        {
            var result = new Dictionary<string, object>();

            var closestStops = context.Stops
                .Where(x => x.Typ == this.stopType)
                .OrderBy(x => ((x.Latitude - latitude) * (x.Latitude - latitude) +
                (x.Longitude - longitude) * (x.Longitude - longitude)))
                .Take(10).ToList();

            result["amountInRadius"] = closestStops.Count;

            if (closestStops.Count > 0)
            {
                result["radius"] = closestStops.Last().Distance(latitude, longitude);
                var closestStop = closestStops.FirstOrDefault();
                result["closest"] = closestStop;

                result["distance"] = closestStop.Distance(latitude, longitude);

            } else
            {
                result["radius"] = 0;
            }

            

            return result;
        }
    }
}