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

            var closestMiddleSchools = context.Schools
                .Where(x => x.Typ == this.schoolType)
                .OrderBy(x => ((x.Latitude - latitude) * (x.Latitude - latitude) +
                (x.Longitude - longitude) * (x.Longitude - longitude)))
                .Take(10).ToList();
            result["radius"] = closestMiddleSchools.ElementAt(9).Distance(latitude, longitude);
            result["amountInRadius"] = 10;
            var closestSchool = closestMiddleSchools.FirstOrDefault();
            result["closest"] = closestSchool;

            result["distance"] = closestSchool.Distance(latitude, longitude);

            return result;
        }
    }
}
