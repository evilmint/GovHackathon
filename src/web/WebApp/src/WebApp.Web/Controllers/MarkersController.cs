using Microsoft.AspNetCore.Mvc;
using System.Collections.Generic;
using System.Linq;
using WebApp.Entity.Data;

namespace WebApp.Web.Controllers
{
    public class MarkersController : Controller
    {
        private ApplicationDbContext context;

        public MarkersController(ApplicationDbContext context)
        {
            this.context = context;
        }

        public IEnumerable<object> GetMarkersFromBoundingBox(double topRightLatitude, double topRightLongitude, double bottomLeftLatitude, double bottomLeftLongitude, string type = "", int limit = -1)
        {
            var result = new List<object>();

            if (type == string.Empty || type == "partyability")
            {
                var events = context.Events
                    .Where(x => x.Latitude < topRightLatitude
                        && x.Longitude < topRightLongitude
                        && x.Latitude > bottomLeftLatitude
                        && x.Longitude > bottomLeftLongitude)
                    .Take(limit >= 0 ? limit : 100)
                    .ToList();
                result.AddRange(events);
            }

            if (type == string.Empty || type == "preschool")
            {
                var primarySchools = context.Schools
                    .Where(x => x.Latitude < topRightLatitude
                        && x.Longitude < topRightLongitude
                        && x.Latitude > bottomLeftLatitude
                        && x.Longitude > bottomLeftLongitude
                        && x.Typ == "00001")
                    .Take(limit >= 0 ? limit : 10)
                    .ToList();
                result.AddRange(primarySchools);
            }

            if (type == string.Empty || type == "primaryschool")
            {
                var primarySchools = context.Schools
                    .Where(x => x.Latitude < topRightLatitude
                        && x.Longitude < topRightLongitude
                        && x.Latitude > bottomLeftLatitude
                        && x.Longitude > bottomLeftLongitude
                        && x.Typ == "00003")
                    .Take(limit >= 0 ? limit : 10)
                    .ToList();
                result.AddRange(primarySchools);
            }

            if (type == string.Empty || type == "middleschool")
            {
                var middleSchools = context.Schools
                    .Where(x => x.Latitude < topRightLatitude
                        && x.Longitude < topRightLongitude
                        && x.Latitude > bottomLeftLatitude
                        && x.Longitude > bottomLeftLongitude
                        && x.Typ == "00004")
                    .Take(limit >= 0 ? limit : 10)
                    .ToList();
                result.AddRange(middleSchools);
            }

            if (type == string.Empty || type == "relic")
            {
                var relics = context.Relics
                    .Where(x => x.Latitude < topRightLatitude
                        && x.Longitude < topRightLongitude
                        && x.Latitude > bottomLeftLatitude
                        && x.Longitude > bottomLeftLongitude)
                    .Take(limit >= 0 ? limit : 10)
                    .ToList();
                result.AddRange(relics);
            }

            if (type == string.Empty || type == "stop" || type == "tramstop" || type == "busstop")
            {
                var stops = context.Stops
                    .Where(x => x.Latitude < topRightLatitude
                        && x.Longitude < topRightLongitude
                        && x.Latitude > bottomLeftLatitude
                        && x.Longitude > bottomLeftLongitude)
                    .Take(limit >= 0 ? limit : 20)
                    .ToList();
                result.AddRange(stops);
            }

            return result;
        }
    }
}
