package com.cwclaims.claimapp.models;

import java.io.Serializable;
import java.util.ArrayList;

public class ReportModel implements Serializable {
    private String id;
    private String user_name;
    private String report;
    private String mr;
    private String claimant_name;
    private String insuredNameDiffernt;
    private String insuredName;
    private String causesOfLoss;
    private String dateLoss;
    private String insuredPersonPresent;
    private String isMortgagee;
    private String mortgagee;
    private String isNoMortgagee;
    private String dateInspected;
    private String timeInspected;
    private String isRoof;
    private String pitch;
    private String layers;
    private String edgeMetal;
    private String edgeMetalCustom;
    private String type;
    private String typeCustom;
    private String age;
    private String story;
    private String dwl_first;
    private String dwl_first_custom;
    private String dwl_second;
    private String dwl_second_custom;
    private String dwl_third;
    private String dwl_third_custom;
    private String dwl_fourth;
    private String dwl_fourth_custom;
    private String dwl_fifth;
    private String dwl_fifth_custom;
    private String dmg_interior;
    private String dmg_interior_custom;
    private String dmg_roof;
    private String dmg_roof_custom;
    private String dmg_front_eleva;
    private String dmg_front_custom;
    private String dmg_left_eleva;
    private String dmg_left_custom;
    private String dmg_back_eleva;
    private String dmg_back_custom;
    private String dmg_right_eleva;
    private String dmg_right_custom;
    private String dmg_notes;
    private String dmg_notes_custom;
    private String misc_title;
    private String misc_title_custom;
    private String misc_op;
    private String misc_depreciation;
    private String misc_depreciation_year;
    private String misc_aps_damage;
    private String misc_aps_damage_custome;
    private String misc_contents;
    private String misc_salvage;
    private String misc_salvage_custom;
    private String subrogation;
    private String subrogation_custom;
    private String LaborMin;
    private String LaborMinAdded;
    private String LaborMinRemoved;
    private String all;
    private String AllCustom;
    private String report_type;
    private String isContractor;
    private String contractorName;
    private String companyName;
    private String From;


    private String created_type;
    private ArrayList<ReportModel> data;

    public ArrayList<ReportModel> getData() {
        return data;
    }

    public String getFrom() {
        return From;
    }

    public void setFrom(String from) {
        From = from;
    }

    public String getIsContractor() {
        return isContractor;
    }

    public void setIsContractor(String isContractor) {
        this.isContractor = isContractor;
    }

    public String getContractorName() {
        return contractorName;
    }

    public void setContractorName(String contractorName) {
        this.contractorName = contractorName;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getReport_type() {
        return report_type;
    }

    public void setReport_type(String report_type) {
        this.report_type = report_type;
    }

    public String getMisc_aps_damage_custome() {
        return misc_aps_damage_custome;
    }

    public void setMisc_aps_damage_custome(String misc_aps_damage_custome) {
        this.misc_aps_damage_custome = misc_aps_damage_custome;
    }

    public String getCreated_type() {
        return created_type;
    }

    public void setCreated_type(String created_type) {
        this.created_type = created_type;
    }


    public String getSubrogation() {
        return subrogation;
    }

    public void setSubrogation(String subrogation) {
        this.subrogation = subrogation;
    }

    public String getSubrogation_custom() {
        return subrogation_custom;
    }

    public void setSubrogation_custom(String subrogation_custom) {
        this.subrogation_custom = subrogation_custom;
    }

    public void setData(ArrayList<ReportModel> data) {
        this.data = data;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getReport() {
        return report;
    }

    public void setReport(String report) {
        this.report = report;
    }

    public String getMr() {
        return mr;
    }

    public void setMr(String mr) {
        this.mr = mr;
    }

    public String getClaimant_name() {
        return claimant_name;
    }

    public void setClaimant_name(String claimant_name) {
        this.claimant_name = claimant_name;
    }

    public String getInsuredNameDiffernt() {
        return insuredNameDiffernt;
    }

    public void setInsuredNameDiffernt(String insuredNameDiffernt) {
        this.insuredNameDiffernt = insuredNameDiffernt;
    }

    public String getInsuredName() {
        return insuredName;
    }

    public void setInsuredName(String insuredName) {
        this.insuredName = insuredName;
    }

    public String getCausesOfLoss() {
        return causesOfLoss;
    }

    public void setCausesOfLoss(String causesOfLoss) {
        this.causesOfLoss = causesOfLoss;
    }

    public String getDateLoss() {
        return dateLoss;
    }

    public void setDateLoss(String dateLoss) {
        this.dateLoss = dateLoss;
    }

    public String getInsuredPersonPresent() {
        return insuredPersonPresent;
    }

    public void setInsuredPersonPresent(String insuredPersonPresent) {
        this.insuredPersonPresent = insuredPersonPresent;
    }

    public String getIsMortgagee() {
        return isMortgagee;
    }

    public void setIsMortgagee(String isMortgagee) {
        this.isMortgagee = isMortgagee;
    }

    public String getMortgagee() {
        return mortgagee;
    }

    public void setMortgagee(String mortgagee) {
        this.mortgagee = mortgagee;
    }

    public String getIsNoMortgagee() {
        return isNoMortgagee;
    }

    public void setIsNoMortgagee(String isNoMortgagee) {
        this.isNoMortgagee = isNoMortgagee;
    }

    public String getDateInspected() {
        return dateInspected;
    }

    public void setDateInspected(String dateInspected) {
        this.dateInspected = dateInspected;
    }

    public String getTimeInspected() {
        return timeInspected;
    }

    public void setTimeInspected(String timeInspected) {
        this.timeInspected = timeInspected;
    }

    public String getIsRoof() {
        return isRoof;
    }

    public void setIsRoof(String isRoof) {
        this.isRoof = isRoof;
    }

    public String getPitch() {
        return pitch;
    }

    public void setPitch(String pitch) {
        this.pitch = pitch;
    }

    public String getLayers() {
        return layers;
    }

    public void setLayers(String layers) {
        this.layers = layers;
    }

    public String getEdgeMetal() {
        return edgeMetal;
    }

    public void setEdgeMetal(String edgeMetal) {
        this.edgeMetal = edgeMetal;
    }

    public String getEdgeMetalCustom() {
        return edgeMetalCustom;
    }

    public void setEdgeMetalCustom(String edgeMetalCustom) {
        this.edgeMetalCustom = edgeMetalCustom;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTypeCustom() {
        return typeCustom;
    }

    public void setTypeCustom(String typeCustom) {
        this.typeCustom = typeCustom;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getStory() {
        return story;
    }

    public void setStory(String story) {
        this.story = story;
    }

    public String getDwl_first() {
        return dwl_first;
    }

    public void setDwl_first(String dwl_first) {
        this.dwl_first = dwl_first;
    }

    public String getDwl_first_custom() {
        return dwl_first_custom;
    }

    public void setDwl_first_custom(String dwl_first_custom) {
        this.dwl_first_custom = dwl_first_custom;
    }

    public String getDwl_second() {
        return dwl_second;
    }

    public void setDwl_second(String dwl_second) {
        this.dwl_second = dwl_second;
    }

    public String getDwl_second_custom() {
        return dwl_second_custom;
    }

    public void setDwl_second_custom(String dwl_second_custom) {
        this.dwl_second_custom = dwl_second_custom;
    }

    public String getDwl_third() {
        return dwl_third;
    }

    public void setDwl_third(String dwl_third) {
        this.dwl_third = dwl_third;
    }

    public String getDwl_third_custom() {
        return dwl_third_custom;
    }

    public void setDwl_third_custom(String dwl_third_custom) {
        this.dwl_third_custom = dwl_third_custom;
    }

    public String getDwl_fourth() {
        return dwl_fourth;
    }

    public void setDwl_fourth(String dwl_fourth) {
        this.dwl_fourth = dwl_fourth;
    }

    public String getDwl_fourth_custom() {
        return dwl_fourth_custom;
    }

    public void setDwl_fourth_custom(String dwl_fourth_custom) {
        this.dwl_fourth_custom = dwl_fourth_custom;
    }

    public String getDwl_fifth() {
        return dwl_fifth;
    }

    public void setDwl_fifth(String dwl_fifth) {
        this.dwl_fifth = dwl_fifth;
    }

    public String getDwl_fifth_custom() {
        return dwl_fifth_custom;
    }

    public void setDwl_fifth_custom(String dwl_fifth_custom) {
        this.dwl_fifth_custom = dwl_fifth_custom;
    }

    public String getDmg_interior() {
        return dmg_interior;
    }

    public void setDmg_interior(String dmg_interior) {
        this.dmg_interior = dmg_interior;
    }

    public String getDmg_interior_custom() {
        return dmg_interior_custom;
    }

    public void setDmg_interior_custom(String dmg_interior_custom) {
        this.dmg_interior_custom = dmg_interior_custom;
    }

    public String getDmg_roof() {
        return dmg_roof;
    }

    public void setDmg_roof(String dmg_roof) {
        this.dmg_roof = dmg_roof;
    }

    public String getDmg_roof_custom() {
        return dmg_roof_custom;
    }

    public void setDmg_roof_custom(String dmg_roof_custom) {
        this.dmg_roof_custom = dmg_roof_custom;
    }

    public String getDmg_front_eleva() {
        return dmg_front_eleva;
    }

    public void setDmg_front_eleva(String dmg_front_eleva) {
        this.dmg_front_eleva = dmg_front_eleva;
    }

    public String getDmg_front_custom() {
        return dmg_front_custom;
    }

    public void setDmg_front_custom(String dmg_front_custom) {
        this.dmg_front_custom = dmg_front_custom;
    }

    public String getDmg_left_eleva() {
        return dmg_left_eleva;
    }

    public void setDmg_left_eleva(String dmg_left_eleva) {
        this.dmg_left_eleva = dmg_left_eleva;
    }

    public String getDmg_left_custom() {
        return dmg_left_custom;
    }

    public void setDmg_left_custom(String dmg_left_custom) {
        this.dmg_left_custom = dmg_left_custom;
    }

    public String getDmg_back_eleva() {
        return dmg_back_eleva;
    }

    public void setDmg_back_eleva(String dmg_back_eleva) {
        this.dmg_back_eleva = dmg_back_eleva;
    }

    public String getDmg_back_custom() {
        return dmg_back_custom;
    }

    public void setDmg_back_custom(String dmg_back_custom) {
        this.dmg_back_custom = dmg_back_custom;
    }

    public String getDmg_right_eleva() {
        return dmg_right_eleva;
    }

    public void setDmg_right_eleva(String dmg_right_eleva) {
        this.dmg_right_eleva = dmg_right_eleva;
    }

    public String getDmg_right_custom() {
        return dmg_right_custom;
    }

    public void setDmg_right_custom(String dmg_right_custom) {
        this.dmg_right_custom = dmg_right_custom;
    }

    public String getDmg_notes() {
        return dmg_notes;
    }

    public void setDmg_notes(String dmg_notes) {
        this.dmg_notes = dmg_notes;
    }

    public String getDmg_notes_custom() {
        return dmg_notes_custom;
    }

    public void setDmg_notes_custom(String dmg_notes_custom) {
        this.dmg_notes_custom = dmg_notes_custom;
    }

    public String getMisc_title() {
        return misc_title;
    }

    public void setMisc_title(String misc_title) {
        this.misc_title = misc_title;
    }

    public String getMisc_title_custom() {
        return misc_title_custom;
    }

    public void setMisc_title_custom(String misc_title_custom) {
        this.misc_title_custom = misc_title_custom;
    }

    public String getMisc_op() {
        return misc_op;
    }

    public void setMisc_op(String misc_op) {
        this.misc_op = misc_op;
    }

    public String getMisc_depreciation() {
        return misc_depreciation;
    }

    public void setMisc_depreciation(String misc_depreciation) {
        this.misc_depreciation = misc_depreciation;
    }

    public String getMisc_depreciation_year() {
        return misc_depreciation_year;
    }

    public void setMisc_depreciation_year(String misc_depreciation_year) {
        this.misc_depreciation_year = misc_depreciation_year;
    }

    public String getMisc_aps_damage() {
        return misc_aps_damage;
    }

    public void setMisc_aps_damage(String misc_aps_damage) {
        this.misc_aps_damage = misc_aps_damage;
    }

    public String getMisc_aps_damage_custom() {
        return misc_aps_damage_custome;
    }

    public void setMisc_aps_damage_custom(String misc_aps_damage_custome) {
        this.misc_aps_damage_custome = misc_aps_damage_custome;
    }

    public String getMisc_contents() {
        return misc_contents;
    }

    public void setMisc_contents(String misc_contents) {
        this.misc_contents = misc_contents;
    }

    public String getMisc_salvage() {
        return misc_salvage;
    }

    public void setMisc_salvage(String misc_salvage) {
        this.misc_salvage = misc_salvage;
    }

    public String getMisc_salvage_custom() {
        return misc_salvage_custom;
    }

    public void setMisc_salvage_custom(String misc_salvage_custom) {
        this.misc_salvage_custom = misc_salvage_custom;
    }

    public String getLaborMin() {
        return LaborMin;
    }

    public void setLaborMin(String laborMin) {
        LaborMin = laborMin;
    }

    public String getLaborMinAdded() {
        return LaborMinAdded;
    }

    public void setLaborMinAdded(String laborMinAdded) {
        LaborMinAdded = laborMinAdded;
    }

    public String getLaborMinRemoved() {
        return LaborMinRemoved;
    }

    public void setLaborMinRemoved(String laborMinRemoved) {
        LaborMinRemoved = laborMinRemoved;
    }

    public String getAll() {
        return all;
    }

    public void setAll(String all) {
        this.all = all;
    }

    public String getAllCustom() {
        return AllCustom;
    }

    public void setAllCustom(String allCustom) {
        AllCustom = allCustom;
    }
}
