namespace WebApp.Entity.Models
{
    public class Marker
    {
        public virtual string Name { get; set; }
        public virtual double Longitude { get; set; }
        public virtual double Latitude { get; set; }
        public virtual string Description { get; set; }
        public virtual string Address { get; set; }
    }
}