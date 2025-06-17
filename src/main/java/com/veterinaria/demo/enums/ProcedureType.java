package com.veterinaria.demo.enums;

import com.veterinaria.demo.exception.ResourceNotFoundException;

public enum ProcedureType {
    CONSULTATION,
    FOLLOW_UP_CONSULTATION,
    EMERGENCY_VISIT,
    VACCINATION,
    BLOOD_TEST,
    DEWORMING,
    FLEA_THICK_TREATMENT,
    WOUND_CARE,
    FLUID_THERAPY,
    CHEMOTHERAPY,
    PHYSICAL_THERAPY,
    XRAY,
    ULTRASOUND,
    URINALYSIS,
    ALLERGY_TEST,
    SPAY_SURGERY,
    NEUTER_SURGERY,
    DENTAL_CLEANING,
    TUMOR_REMOVAL,
    ORTHOPEDIC_SURGERY,
    SOFT_TISSUE_SURGERY,
    CARDIAC_SURGERY,
    OTHER;

    private ProcedureType parseProcedureType(String type) {
        try {
            return ProcedureType.valueOf(type.toUpperCase());
        } catch (IllegalArgumentException exc) {
            throw new ResourceNotFoundException("Procedure type not found: " + type);
        }
    }
}
