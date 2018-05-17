package es.accenture.mlgia.dto;

import java.sql.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author josemaria.palma
 *
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MessageDTO {
 
    private String messageIn;
    
    private String messageOut;
    
    private String messagePredictOut;
 
    private Date date;
    
    private String conversationId;
    
}
