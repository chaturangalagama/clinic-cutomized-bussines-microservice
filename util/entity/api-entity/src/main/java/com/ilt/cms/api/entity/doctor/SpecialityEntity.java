package com.ilt.cms.api.entity.doctor;

import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class SpecialityEntity {
    public enum Practice {
        /*GASTROENTEROLOGY, GENERAL_PRACTITIONER, OPHTHALMOLOGY, ORTHOPEDIC,
        PEDIATRICS, AESTHETIC, PSYCHOLOGY, CHILD_DEVELOPMENT_PSYCHOLOGY,
        DOCTOR, BREAST_SURGERY, COLORECTAL, A_AND_E, EAR_NOSE_THROAT,
        RESPIRATORY, GENERAL_SURGERY, DENTAL, CARDIOLOGY,
        OBSTETRICS_AND_GYNAECOLOGY, OTHER*/

        ANAESTHESIOLOGY, ENDOCRINOLOGY, GP, OTHER,

        // newly appended speciality
        ASSISTANT_TEACHER, CLINIC, DOCTOR, EARLY_INTERVENTION,
        ED_THERAPIST, EIP, EIP_TEACHER, FEEDING_CLINIC, HYGENIST,
        LACTATION_CONSULTANT, OCCU_THERAPIST, OCCUPATIONAL_THERAPY, OPTOMETRIST,
        PAEDIATRICIAN, PC, PHYSIO_THERAPIST, PHYSIOTHERPAIST,
        PSYCHOLOGIST, SENIOR_DIETICIAN, SENIOR_OT, SENIOR_PSYCHOLOGIST, SLEEP,
        SNR_EIP, SNR_PHYSIOTHERAPIST, SNR_SPEECH_THERAPIST, SNR_THERAPIST,
        SPEECH_THERAPIST, SR_SPEECH_THERAPIST, TEACHER, THERAPIST, TRACKING,

        A_AND_E, ALLERGY, BREAST_SURGERY, CARDIOLOGY, CHILD_DEVELOPMENT_CENTER, CHILD_SPEECH_THERAPIST,
        CHILDRENS_EMERGENCY, COLORECTAL, DENTAL, DERMATOLOGY, EAR_NOSE_THROAT, GASTROENTEROLOGIST,
        GENERAL_PRACTITIONER, GENERAL_SURGERY, HEALTH_SCREENING, NEUROLOGY, OBSTETRICS_AND_GYNAECOLOGY,
        OPHTHALMOLOGY, ORTHOPEDIC, PEDIATRICS, PHARMACIST, PHYSIOTHERAPIST, PSYCHEALTH, PSYCHIATRIST,
        RESPIRATORY, UROLOGIST,

        PSYCHOLOGY, AESTHETIC, CHILD_DEVELOPMENT_PSYCHOLOGY, GASTROENTEROLOGY
    }
    private String id;
    private Practice practice;
    private List<String> clinicIds;
}
