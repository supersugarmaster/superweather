package com.superweather.app.util;

import com.superweather.app.db.SuperWeatherDB;
import com.superweather.app.model.City;
import com.superweather.app.model.County;
import com.superweather.app.model.Province;

import android.R.bool;
import android.text.TextUtils;

public class Utility {
	//解析和处理服务器返回的省级数据
	public synchronized static boolean handleProvincesResponse(SuperWeatherDB
		superWeatherDB, String response){
		if(!TextUtils.isEmpty(response)){
			String[] allProvinces = response.split(",");
			if(allProvinces != null && allProvinces.length > 0){
				for(String p : allProvinces){
					String[] array = p.split("\\|");
					Province province = new Province();
					province.setProvinceCode(array[0]);
					province.setProvinceName(array[1]);
					superWeatherDB.saveProvince(province);
				}
				return true;
			}
		}
		return false;
	}
	
	//解析和处理服务器返回的市级数据
	public synchronized static boolean handleCitiesResponse(SuperWeatherDB
		superWeatherDB, String response, int provinceId){
		if(!TextUtils.isEmpty(response)){
			String[] allCities = response.split(",");
			if(allCities != null && allCities.length > 0){
				for(String p : allCities){
					String[] array = p.split("\\|");
					City city = new City();
					city.setCityCode(array[0]);
					city.setCityName(array[1]);
					city.setProvinceId(provinceId);
					superWeatherDB.saveCity(city);
				}
				return true;
			}
		}
		return false;
	}

	//解析和处理服务器返回的县级数据
	public synchronized static boolean handleCountiesResponse(SuperWeatherDB
		superWeatherDB, String response, int cityId){
		if(!TextUtils.isEmpty(response)){
			String[] allCounties = response.split(",");
			if(allCounties != null && allCounties.length > 0){
				for(String p : allCounties){
					String[] array = p.split("\\|");
					County county = new County();
					county.setCountyCode(array[0]);
					county.setCountyName(array[1]);
					county.setCityId(cityId);
					superWeatherDB.saveCounty(county);
				}
				return true;
			}
		}
		return false;
	}
}