using Microsoft.AspNetCore.Mvc;
using System.Collections.Generic;
using System.Linq;
using WebApp.Entity.Data;
using WebApp.Entity.Models;
using WebApp.Web.Models;
using System;

namespace WebApp.Web.Controllers
{
    public class MarkersController : Controller
    {
        private ApplicationDbContext context;

        public MarkersController(ApplicationDbContext context)
        {
            this.context = context;
        }

        public IEnumerable<object> GetMarkersFromBoundingBox(double topLeftLattitude, double topLeftLongitude, double bottomRightLattitude, double bottomRightLongitude)
        {
            return context.Events.Where(x=> (x.Latitude < bottomRightLattitude && x.Latitude > topLeftLattitude &&
                x.Longitude < bottomRightLongitude && x.Longitude > topLeftLongitude)).Take(100);
        }
    }
}