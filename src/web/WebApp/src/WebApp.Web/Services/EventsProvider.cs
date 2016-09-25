using System;
using System.Collections.Generic;
using System.Linq;
using WebApp.Entity.Data;
using WebApp.Entity.Models;

namespace WebApp.Web.Services
{
    public class EventsProvider : IDataProvider
    {
        private readonly ApplicationDbContext _dbContext;

        public EventsProvider(ApplicationDbContext dbContext)
        {
            _dbContext = dbContext;
        }

        public string Name => "partyability";

        public Dictionary<string, object> GetSummary(double latitude, double longitude)
        {
            var closestPartyability = _dbContext.Partyabilities
                .OrderBy(p => (p.Latitude - latitude) * (p.Latitude - latitude) + (p.Longitude - longitude) * (p.Longitude - longitude))
                .FirstOrDefault();

            var partyabilities = _dbContext.Partyabilities
                .Where(p => p.Latitude == closestPartyability.Latitude && p.Longitude == closestPartyability.Longitude)
                .OrderBy(p => p.Month)
                .ToList();

            var dupa = new[] {
                "2015-09",
                "2015-10",
                "2015-11",
                "2015-12",
                "2016-01",
                "2016-02",
                "2016-03",
                "2016-04",
                "2016-05",
                "2016-06",
                "2016-07",
                "2016-08"
            };

            var resultPartyabilities = dupa.Select(dup => partyabilities.SingleOrDefault(p => p.Month == dup) ?? new Partyability { Latitude = closestPartyability.Latitude, Longitude = closestPartyability.Longitude, Month = dup, Value = 0 });

            var result = new Dictionary<string, object>();
            result["partyability"] = resultPartyabilities;

            return result;
        }
    }
}
