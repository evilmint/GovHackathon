using System;
using System.Collections.Generic;
using Microsoft.EntityFrameworkCore.Migrations;

namespace WebApp.Web.Data.Migrations
{
    public partial class housesummaryfix : Migration
    {
        protected override void Up(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropForeignKey(
                name: "FK_Metrics_HouseSummaries_HouseSummaryId",
                table: "Metrics");

            migrationBuilder.DropIndex(
                name: "IX_Metrics_HouseSummaryId",
                table: "Metrics");

            migrationBuilder.DropColumn(
                name: "HouseSummaryId",
                table: "Metrics");

            migrationBuilder.AddColumn<int>(
                name: "SummaryId",
                table: "Metrics",
                nullable: true);

            migrationBuilder.CreateIndex(
                name: "IX_Metrics_SummaryId",
                table: "Metrics",
                column: "SummaryId");

            migrationBuilder.AddForeignKey(
                name: "FK_Metrics_HouseSummaries_SummaryId",
                table: "Metrics",
                column: "SummaryId",
                principalTable: "HouseSummaries",
                principalColumn: "Id",
                onDelete: ReferentialAction.Restrict);
        }

        protected override void Down(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropForeignKey(
                name: "FK_Metrics_HouseSummaries_SummaryId",
                table: "Metrics");

            migrationBuilder.DropIndex(
                name: "IX_Metrics_SummaryId",
                table: "Metrics");

            migrationBuilder.DropColumn(
                name: "SummaryId",
                table: "Metrics");

            migrationBuilder.AddColumn<int>(
                name: "HouseSummaryId",
                table: "Metrics",
                nullable: true);

            migrationBuilder.CreateIndex(
                name: "IX_Metrics_HouseSummaryId",
                table: "Metrics",
                column: "HouseSummaryId");

            migrationBuilder.AddForeignKey(
                name: "FK_Metrics_HouseSummaries_HouseSummaryId",
                table: "Metrics",
                column: "HouseSummaryId",
                principalTable: "HouseSummaries",
                principalColumn: "Id",
                onDelete: ReferentialAction.Restrict);
        }
    }
}
