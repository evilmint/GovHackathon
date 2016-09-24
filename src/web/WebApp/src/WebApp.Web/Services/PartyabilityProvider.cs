using System;
using System.Collections.Generic;

namespace WebApp.Web.Services
{
    public class PartyabilityProvider : IDataProvider
    {
        public string Name => "partyability";

        public Dictionary<string, object> Get(double latitude, double longitude)
        {
            throw new NotImplementedException();
        }
    }
}
