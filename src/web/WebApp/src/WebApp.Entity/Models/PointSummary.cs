using Newtonsoft.Json;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations.Schema;

namespace WebApp.Entity.Models
{
    public class PointSummary
    {
        public int Id { get; set; }
        public virtual Point Point { get; set; }
        public virtual ICollection<PointMetric> Metrics { get; set; } = new List<PointMetric>();

        [NotMapped]
        //todo to się nie serializuje do jsona ale nie ma być w bazie!
        public double Score { get; set; } = 0;
    }
}