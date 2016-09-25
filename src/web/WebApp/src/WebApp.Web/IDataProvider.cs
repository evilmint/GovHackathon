using System.Collections.Generic;

namespace WebApp.Web.Services
{
    public interface IDataProvider
    {
        string Name { get; }

        Dictionary<string, object> GetSummary(double latitude, double longitude);
    }
}