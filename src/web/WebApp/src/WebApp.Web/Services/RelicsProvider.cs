using System;
using System.Collections.Generic;
using System.Linq;
using WebApp.Entity.Data;

namespace WebApp.Web.Services
{
    public class RelicsProvider : IDataProvider
    {
        private readonly ApplicationDbContext _dbContext;

        public string Name => "relics";

        public RelicsProvider(ApplicationDbContext dbContext)
        {
            _dbContext = dbContext;
        }

        public Dictionary<string, object> Get(double latitude, double longitude)
        {
            var closestRelics = _dbContext.Relics
                .OrderBy(x => ((x.Latitude - latitude) * (x.Latitude - latitude) +
                (x.Longitude - longitude) * (x.Longitude - longitude)))
                .Take(10)
                .ToList();

            var closestRelic = closestRelics.FirstOrDefault();

            var result = new Dictionary<string, object>();
            result["radius"] = closestRelics.ElementAt(9).Distance(latitude, longitude);
            result["amountInRadius"] = closestRelics.Count;
            result["closest"] = closestRelic;
            result["distance"] = closestRelic.Distance(latitude, longitude);

            return result;
        }
    }
}
