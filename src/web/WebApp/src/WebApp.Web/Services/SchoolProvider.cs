using System;
using System.Collections.Generic;
using System.Linq;
using WebApp.Entity.Data;
using WebApp.Web.Helpers;

namespace WebApp.Web.Services
{
    public class SchoolProvider : IDataProvider
    {
        public string Name
        {
            get
            {
                switch (this.schoolType)
                {
                    case "00001":
                        return "preschool";

                    case "00003":
                        return "primaryschool";

                    case "00004":
                        return "middleschool";
                }
                return "otherschool";
            }
        }
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
            result["distance"] = Distance.Compute(closestMiddleSchool.Latitude, latitude, closestMiddleSchool.Longitude, longitude);

            return result;
        }
    }
}
