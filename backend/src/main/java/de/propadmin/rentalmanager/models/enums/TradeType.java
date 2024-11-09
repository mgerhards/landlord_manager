package de.propadmin.rentalmanager.models.enums;

import lombok.Getter;

@Getter
public enum TradeType {
    PLUMBING("Sanitär"),
    ELECTRICAL("Elektrik"),
    HEATING("Heizung"),
    CARPENTRY("Tischlerei"),
    PAINTING("Malerei"),
    ROOFING("Dachdecker"),
    MASONRY("Maurer"),
    LOCKSMITH("Schlüsseldienst"),
    GARDENING("Gartenbau"),
    CLEANING("Reinigung"),
    HVAC("Klimaanlage"),
    FLOORING("Bodenleger"),
    GLAZIER("Glaser"),
    PEST_CONTROL("Schädlingsbekämpfung");

    private final String germanName;

    TradeType(String germanName) {
        this.germanName = germanName;
    }
}