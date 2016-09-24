using Microsoft.AspNetCore.Mvc;
using WebApp.Entity.Data;

namespace WebApp.Web.Controllers
{
    public class SchoolController : Controller
    {
        private ApplicationDbContext context;

        public SchoolController(ApplicationDbContext context)
        {
            this.context = context;
        }

        //IEnumerable<School> GetSchools(double x, double y, double radius)
        //{
        //    var request = new GeocodingRequest();
        //    request.Address
        //}
    }
}