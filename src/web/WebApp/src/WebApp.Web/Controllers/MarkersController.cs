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

            if (type == string.Empty || type == "events")
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

            if (type == string.Empty || type == "preSchools")
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

            if (type == string.Empty || type == "primarySchools")
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

            if (type == string.Empty || type == "middleSchools")
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

            if (type == string.Empty || type == "relics")
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

            if (type == string.Empty || type == "stops")
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
