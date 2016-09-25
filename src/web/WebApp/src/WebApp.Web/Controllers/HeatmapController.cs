using Microsoft.EntityFrameworkCore;
using System;
using System.Collections.Generic;
using System.Linq;
using WebApp.Entity.Data;
using WebApp.Entity.Models;

namespace WebApp.Web.Controllers
{
    public class HeatmapController
    {
        private const int precision = 10;
        private ApplicationDbContext context;
        private List<Point> points = new List<Point>();

        public HeatmapController(ApplicationDbContext context)
        {
            this.context = context;
        }

        public string Calculate()
        {
            var dataProviders = new DataProvidersFactory().Get(this.context);

            double startlat, startlong, endlat, endlong;

            startlat = 51.123721;
            startlong = 17.001171;
            endlat = 51.084271;
            endlong = 17.062626;

            double distlat, distlong;
            distlat = Math.Abs(startlat - endlat);
            distlong = Math.Abs(startlong - endlong);

            double latstep = distlat / precision;
            double longstep = distlong / precision;

            for (int lat = 0; lat < precision; lat++)
            {
                for (int lon = 0; lon < precision; lon++)
                {
                    points.Add(new Point { Latitude = endlat + (lat * latstep), Longitude = endlong + (lon * longstep) });
                }
            }

            context.Points.AddRange(points);

            var pointSummaries = new List<PointSummary>();
            foreach (Point point in points)
            {
                var pointSummary = new PointSummary();
                foreach (var dataProvider in dataProviders)
                {
                    if (dataProvider.Name == "partyability")
                    {
                        //TODO calculate metric for partyability
                        //average from all months?

                        var metric = new PointMetric();

                        metric.Value = context.Partyabilities
                        .OrderBy(p => (p.Latitude - point.Latitude) * (p.Latitude - point.Latitude) + (p.Longitude - point.Longitude) * (p.Longitude - point.Longitude)).Average(x => x.Value);
                        metric.Value /= 10;

                        metric.Type = dataProvider.Name;

                        pointSummary.Metrics.Add(metric);

                        //var dataProviderResult = dataProvider.GetSummary(house.Latitude, house.Longitude);
                        //var Metric = new Metric();
                        //Metric.Type = dataProvider.Name;
                        //Metric.Value = (double)(((Partyability[])dataProviderResult.FirstOrDefault().Value).ToList().Average(x => x.Value));
                        //houseSummary.Metrics.Add(Metric);
                    }
                    else if (dataProvider.Name == "relic") //zagęszczenie bo odległość do najbliższego zabytku nie ma sensu więc biorę radius, im mniejszy tym większe zagęszczenie
                    {
                        var dataProviderResult = dataProvider.GetSummary(point.Latitude, point.Longitude);
                        var Metric = new PointMetric();
                        Metric.Type = dataProvider.Name;
                        Metric.Value = (double)(dataProviderResult.Where(x => x.Key == "radius").FirstOrDefault().Value);
                        pointSummary.Metrics.Add(Metric);
                    }
                    else
                    {
                        var dataProviderResult = dataProvider.GetSummary(point.Latitude, point.Longitude);
                        var Metric = new PointMetric();
                        Metric.Type = dataProvider.Name;
                        Metric.Value = (double)(dataProviderResult.Where(x => x.Key == "distance").FirstOrDefault().Value);
                        pointSummary.Metrics.Add(Metric);
                    }
                }
                pointSummary.Point = point;
                context.PointSummaries.Add(pointSummary);
            }
            context.SaveChanges();
            return "ok";
        }

        public IEnumerable<Point> Search(string searchParams)
        {
            string[] parameters = searchParams.Split(';');
            var pointSummaries = context.PointSummaries
                .Include(x => x.Point)
                .Include(x => x.Metrics)
                .ToList();

            foreach (string parameter in parameters)
            {
                string[] parts = parameter.Split(':');
                string type = parts[0];
                double value = Convert.ToDouble(parts[1]);

                foreach (PointSummary pointSummary in pointSummaries)
                {
                    PointMetric metric = pointSummary.Metrics.Where(x => x.Type == type).FirstOrDefault();
                    if (type == "relic")
                    { //to zagęszczenie więc odwrotnie proporcjonalne
                        pointSummary.Score += metric.Value * (1 / value);
                        pointSummary.Point.Score += metric.Value * (1 / value);
                    }
                    else
                    {
                        pointSummary.Score += metric.Value * value;
                        pointSummary.Point.Score += metric.Value * value;
                    }
                }
            }

            return pointSummaries.OrderBy(x => x.Score).Select(x => x.Point);
        }
    }
}