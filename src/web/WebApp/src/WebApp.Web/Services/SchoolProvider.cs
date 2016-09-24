using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using WebApp.Entity.Data;
using WebApp.Entity.Models;

namespace WebApp.Web.Services
{
    public class SchoolProvider : IDataProvider
    {
        public string Name { get { return "school"; } }
        private ApplicationDbContext context;
        private string schoolType;

        public SchoolProvider(ApplicationDbContext dbContext, string schoolType)
        {
            this.context = dbContext;
            this.schoolType = schoolType;
        }

        public Dictionary<string, object> Get(double latitude, double longitude)
        {
            var result = new Dictionary<string, object>();

            var closestMiddleSchool = context.Schools
                .Where(x => x.Typ == this.schoolType)
                .OrderBy(x => Math.Sqrt(Math.Abs(x.Latitude - latitude) + Math.Abs(x.Longitude - longitude))).FirstOrDefault();

            result["closest"] = closestMiddleSchool;
            result["distance"] = Math.Sqrt(Math.Abs(closestMiddleSchool.Latitude - latitude) + Math.Abs(closestMiddleSchool.Longitude - longitude)) * 60.71;

            return result;
        }
    }
}
