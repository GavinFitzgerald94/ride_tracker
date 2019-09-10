package com.pluralsight.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.pluralsight.repository.util.RiderRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.pluralsight.model.Ride;

@Repository("rideRepository")
public class RideRepositoryImpl implements RideRepository {

    //Just practicing some git stuff

	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Override
	public Ride createRide(Ride ride){
		//jdbcTemplate.update("insert into ride (name, duration) values (?,?)", ride.getName(), ride.getDuration());

		KeyHolder keyHolder = new GeneratedKeyHolder();
		jdbcTemplate.update(connection -> {
			PreparedStatement ps = connection.prepareStatement("insert into ride (name, duration) values (?,?)", new String[]{"id"});
			ps.setString(1, ride.getName());
			ps.setInt(2, ride.getDuration());
			return ps;
		}, keyHolder);

		Number id = keyHolder.getKey();

		return getRide(id.intValue());
	}

	public Ride getRide(Integer id){
		Ride ride = jdbcTemplate.queryForObject("select * from ride where id = ?", new RiderRowMapper(), id);
		return ride;
	}

	@Override
	public List<Ride> getRides() {
		List<Ride> rides = jdbcTemplate.query("select * from ride", new RiderRowMapper());

		return rides;
	}
	
}
