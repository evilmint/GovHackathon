namespace WebApp.Entity.Models
{
    public class PointMetric
    {
        public int Id { get; set; }
        public string Type { get; set; }
        public double Value { get; set; }
        public virtual PointSummary Summary { get; set; }
    }
}
