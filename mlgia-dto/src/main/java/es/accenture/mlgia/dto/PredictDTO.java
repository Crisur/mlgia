package es.accenture.mlgia.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PredictDTO {

	private Integer parkingId;
	
	private String date;
	
	private String time;
}
