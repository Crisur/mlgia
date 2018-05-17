package es.accenture.mlgia.dto;

import java.util.Arrays;

public enum ParkingType {
	
	SALITRE("Salitre", 1),
	CERVANTES("Cervantes", 2),
	PALO("El Palo", 3),
	ANDALUCIA("Av. de Andalucía", 4),
	CAMAS("Camas", 5),
	HUMILLADERO("Cruz De Humilladero", 6),
	ALCAZABA("Alcazaba", 8),
	SAN_JUAN("San Juan De La Cruz", 9),
	MARINA("Pz. de la Marina", 10),
	TEJON_RODRIGUEZ("Tejón y Rodriguez", 12),
	CARMEN("El Carmen", 138),
	MARMOLES("Mármoles", 139),
	LIMONAR("El Limonar", 140),
	BAILEN("Bailén", 142);
	
	private String name;
	
	private int id;
	
	private ParkingType(String name, int id) {		
		this.name = name;
		this.id = id;		
	}
	
	public String getName() {
		return this.name;
	}
	
	public int getId() {
		return this.id;
	}
	
	public static Integer getIdByName(String name) {
		
		Integer id = null;
		
		ParkingType parking =  getParkingByName(name);
		
		if (parking != null) {
			id = parking.getId();
		}
		
		return id;
		
	}
	
	public static ParkingType getParkingByName(String name) {

		return Arrays.asList(ParkingType.values()).stream()
				.filter(p -> p.getName().toLowerCase().trim().equals(name.toLowerCase().trim()))
				.findFirst()
				.orElse(null);
	}
	
}
