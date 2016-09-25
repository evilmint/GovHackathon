using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using WebApp.Entity.Data;
using WebApp.Web.Controllers;
using WebApp.Web.Services;

namespace WebApp.Web
{
    public class DataProvidersFactory
    {
        public IDataProvider[] Get(ApplicationDbContext context)
        {
            return new IDataProvider[]
            {
                new PartyabilityProvider(context),
                new RelicsProvider(context),
                new SchoolProvider(context, "00004"),
                new SchoolProvider(context, "00001"),
                new SchoolProvider(context, "00003"),
                new StopsProvider(context, StopType.Bus),
                new StopsProvider(context, StopType.Tram)
            };
        }
    }
}
