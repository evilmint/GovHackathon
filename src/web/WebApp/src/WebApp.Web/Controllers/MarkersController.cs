using Microsoft.AspNetCore.Mvc;
using System.Collections.Generic;
using System.Linq;
using WebApp.Entity.Data;
using WebApp.Web.Services;

namespace WebApp.Web.Controllers
{
    public class MarkersController : Controller
    {
        private ApplicationDbContext context;

        public MarkersController(ApplicationDbContext context)
        {
            this.context = context;
        }

        public IEnumerable<object> GetMarkersFromBoundingBox(double topRightLatitude, double topRightLongitude, double bottomLeftLatitude, double bottomLeftLongitude)
        {
            var bb = new BoundingBox(topRightLatitude, topRightLongitude, bottomLeftLatitude, bottomLeftLongitude);

            List<object> result = new List<object>();
            var events = context.Events.Where(x => (x.Latitude < topRightLatitude && x.Longitude < topRightLongitude &&
                x.Latitude > bottomLeftLatitude && x.Longitude > bottomLeftLongitude)).Take(100);
            result.AddRange(events);

            var middleSchools = context.Schools.Where(x => (x.Latitude < topRightLatitude && x.Longitude < topRightLongitude &&
                x.Latitude > bottomLeftLatitude && x.Longitude > bottomLeftLongitude) && x.Typ == "00004").Take(10);

            return result;
        }
    }
}