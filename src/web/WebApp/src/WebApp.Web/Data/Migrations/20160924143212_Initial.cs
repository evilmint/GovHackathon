using System;
using System.Collections.Generic;
using Microsoft.EntityFrameworkCore.Migrations;

namespace WebApp.Web.Data.Migrations
{
    public partial class Initial : Migration
    {
        protected override void Up(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.CreateTable(
                name: "Schools",
                columns: table => new
                {
                    Id = table.Column<int>(nullable: false),
                    Klasawielk = table.Column<string>(name: "Klasa wielk#", maxLength: 255, nullable: true),
                    Kodpoczt = table.Column<string>(name: "Kod poczt#", maxLength: 255, nullable: true),
                    Miejscowość = table.Column<string>(maxLength: 255, nullable: true),
                    Nazwaszkołyplacówki = table.Column<string>(name: "Nazwa szkoły, placówki", maxLength: 255, nullable: true),
                    Nrdomu = table.Column<string>(name: "Nr domu", maxLength: 255, nullable: true),
                    Patron = table.Column<string>(maxLength: 255, nullable: true),
                    Poczta = table.Column<string>(maxLength: 255, nullable: true),
                    POLgm = table.Column<string>(maxLength: 255, nullable: true),
                    POLpow = table.Column<string>(maxLength: 255, nullable: true),
                    POLwoj = table.Column<string>(maxLength: 255, nullable: true),
                    Publiczność = table.Column<string>(maxLength: 255, nullable: true),
                    Telefon = table.Column<string>(maxLength: 255, nullable: true),
                    Typ = table.Column<string>(maxLength: 255, nullable: true),
                    Ulica = table.Column<string>(maxLength: 255, nullable: true),
                    WWW = table.Column<string>(maxLength: 255, nullable: true),
                    Złożoność = table.Column<string>(maxLength: 255, nullable: true)
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK_Schools", x => x.Id);
                });
        }

        protected override void Down(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropTable(
                name: "Schools");
        }
    }
}
