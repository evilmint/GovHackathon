﻿using System;
using System.Collections.Generic;

namespace WebApp.Entity.Models
{
    public partial class School
    {
        public int Id { get; set; }
        public string Polwoj { get; set; }
        public string Polpow { get; set; }
        public string Polgm { get; set; }
        public string Miejscowość { get; set; }
        public string KlasaWielk { get; set; }
        public string Typ { get; set; }
        public string Złożoność { get; set; }
        public string NazwaSzkołyPlacówki { get; set; }
        public string Patron { get; set; }
        public string Ulica { get; set; }
        public string NrDomu { get; set; }
        public string KodPoczt { get; set; }
        public string Poczta { get; set; }
        public string Telefon { get; set; }
        public string Www { get; set; }
        public string Publiczność { get; set; }
        public double lon { get; set; }
        public double lat { get; set; }
    }
}
