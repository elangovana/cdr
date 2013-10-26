package com.ae.assignment.cdrproject.cdrservice.repository;

import java.util.List;

import com.ae.assignment.cdrproject.cdrservice.model.StatsCallPromo;

public interface RepositoryStatsCallPromo extends RepositoryGeneric<StatsCallPromo, String> {

	String reportPromoCount(int lastN);
}
