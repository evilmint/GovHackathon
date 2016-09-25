using Newtonsoft.Json;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations.Schema;

namespace WebApp.Entity.Models
{
    public class HouseSummary
    {
        public int Id { get; set; }
        public virtual House House { get; set; }
        public virtual ICollection<Metric> Metrics { get; set; } = new List<Metric>();

        [NotMapped]
        //todo to się nie serializuje do jsona ale nie ma być w bazie!
        public double Score { get; set; } = 0;
    }
}