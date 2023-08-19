package dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;


@JsonIgnoreProperties(ignoreUnknown = true)
public record ApiResponse(List<PlaceInfoResponse> documents) {

	@JsonIgnoreProperties(ignoreUnknown = true)
	public record PlaceInfoResponse(
		String place_url, String place_name, String address_name, String phone, double distance) {
	}
}
