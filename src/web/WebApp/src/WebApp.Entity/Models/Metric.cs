namespace WebApp.Entity.Models
{
    public class Metric
    {
        public int Id { get; set; }
        public string Type { get; set; }
        public double Value { get; set; }
        public virtual HouseSummary Summary { get; set; }
    }
}
