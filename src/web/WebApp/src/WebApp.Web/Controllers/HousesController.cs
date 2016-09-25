using Microsoft.EntityFrameworkCore;
using System;
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
                        //TODO calculate metric for partyability
                        //average from all months?
                    }
                    else if (dataProvider.Name == "relic") //zagęszczenie bo odległość do najbliższego zabytku nie ma sensu więc biorę radius, im mniejszy tym większe zagęszczenie
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

        public IEnumerable<House> Search(string searchParams)
        {
            string[] parameters = searchParams.Split(';');
            var houseSummaries = context.HouseSummaries
                .Include(x => x.House)
                .Include(x => x.Metrics)
                .ToList();

            foreach (string parameter in parameters)
            {
                string[] parts = parameter.Split(':');
                string type = parts[0];
                double value = Convert.ToDouble(parts[1]);

                if (type == "minprice")
                {
                    houseSummaries = houseSummaries.Where(x => x.House.Price >= value).ToList();
                }
                else if (type == "maxprice")
                {
                    houseSummaries = houseSummaries.Where(x => x.House.Price <= value).ToList();
                }
                else if (type == "minarea")
                {
                    houseSummaries = houseSummaries.Where(x => x.House.Area >= value).ToList();
                }
                else if (type == "maxarea")
                {
                    houseSummaries = houseSummaries.Where(x => x.House.Area <= value).ToList();
                }

                foreach (HouseSummary houseSummary in houseSummaries)
                {
                    Metric metric = houseSummary.Metrics.Where(x => x.Type == type).FirstOrDefault();
                    if (type == "relic")
                    { //to zagęszczenie więc odwrotnie proporcjonalne
                        houseSummary.Score += metric.Value * (1 / value);
                        houseSummary.House.Score += metric.Value * (1 / value);

                    }
                    else
                    {
                        houseSummary.Score += metric.Value * value;
                        houseSummary.House.Score += metric.Value * value;
                    }
                }
            }

            return houseSummaries.OrderBy(x => x.Score).Select(x => x.House);
        }
    }
}