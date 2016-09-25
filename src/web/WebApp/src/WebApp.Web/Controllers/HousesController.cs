using System.Collections.Generic;
using System.Linq;
using WebApp.Entity.Data;
using WebApp.Entity.Models;

namespace WebApp.Web.Controllers
{
    public class HousesController
    {
        private ApplicationDbContext context;

        public HousesController(ApplicationDbContext context)
        {
            this.context = context;
        }

        public string Calculate()
        {
            var dataProviders = new DataProvidersFactory().Get(this.context);
            var houseSummaries = new List<HouseSummary>();
            foreach (House house in context.Houses.ToList())
            {
                var houseSummary = new HouseSummary();
                foreach (var dataProvider in dataProviders)
                {
                    if (dataProvider.Name == "partyability")
                    {
                        //calculate metric for partyability
                        //average from all months?
                    }
                    else if (dataProvider.Name == "relic")
                    {
                        var dataProviderResult = dataProvider.GetSummary(house.Latitude, house.Longitude);
                        var Metric = new Metric();
                        Metric.Type = dataProvider.Name;
                        Metric.Value = (double)(dataProviderResult.Where(x => x.Key == "radius").FirstOrDefault().Value);
                        houseSummary.Metrics.Add(Metric);
                    }
                    else
                    {
                        var dataProviderResult = dataProvider.GetSummary(house.Latitude, house.Longitude);
                        var Metric = new Metric();
                        Metric.Type = dataProvider.Name;
                        Metric.Value = (double)(dataProviderResult.Where(x => x.Key == "distance").FirstOrDefault().Value);
                        houseSummary.Metrics.Add(Metric);
                    }
                }
                houseSummary.House = house;
                context.HouseSummaries.Add(houseSummary);
            }
            context.SaveChanges();
            return "ok";
        }
    }
}