using System.Collections.Generic;

namespace WebApp.Entity.Models
{
    public class HouseSummary
    {
        public int Id { get; set; }
        public virtual House House { get; set; }
        public virtual ICollection<Metric> Metrics { get; set; } = new List<Metric>();
    }
}