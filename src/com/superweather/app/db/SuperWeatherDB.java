package com.superweather.app.db;

import java.util.ArrayList;
import java.util.List;

import com.superweather.app.model.City;
import com.superweather.app.model.County;
import com.superweather.app.model.Province;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class SuperWeatherDB {
	//数据库名
	public static final String DB_NAME = "super_weather";
	//数据库版本
	public static final int VERSION = 1;
	private static SuperWeatherDB superWeatherDB;
	private SQLiteDatabase db;
	
	private SuperWeatherDB(Context context){
		SuperWeatherOpenHelper dbhelper = new SuperWeatherOpenHelper(context, DB_NAME, null, VERSION);
		db = dbhelper.getWritableDatabase();
	}
	
	//获取SuperWeatherDB实例
	public synchronized static SuperWeatherDB getInstance(Context context){
		if (superWeatherDB == null){
			superWeatherDB = new SuperWeatherDB(context);
		}
		return superWeatherDB;
	}
	
	//将province实例存储到数据库
	public void saveProvince(Province province){
		if(province != null){
			ContentValues values = new ContentValues();
			values.put("province_name", province.getProvinceName());
			values.put("province_code", province.getProvinceCode());
			db.insert("Province", null, values);
		}
	}
	
	//从数据库读取全国所有的省份信息
	public List<Province> loadProvinces(){
		List<Province> list = new ArrayList<Province>();
		Cursor cursor = db.query("Province", null , null , null , null , null , null);
		if(cursor.moveToFirst()){
			do{
				Province province = new Province();
				province.setId(cursor.getInt(cursor.getColumnIndex("id")));
				province.setProvinceCode(cursor.getString(cursor.getColumnIndex("province_code")));
				province.setProvinceName(cursor.getString(cursor.getColumnIndex("province_name")));
				list.add(province);
			}while(cursor.moveToNext());
		}
		return list;
	}
	
	//将city实例存储到数据库
	public void saveCity(City city){
		if(city != null){
			ContentValues values = new ContentValues();
			values.put("city_name", city.getCityName());
			values.put("city_code", city.getCityCode());
			values.put("province_id", city.getProvinceId());
			db.insert("City", null, values);
		}
	}
	
	//从数据库读取某省下所有的城市信息
	public List<City> loadCities(int provinceId){
		List<City> list = new ArrayList<City>();
		Cursor cursor = db.query("City", null , "province_id = ?" , new String[]{String.valueOf(provinceId)} , null , null , null);
		if(cursor.moveToFirst()){
			do{
				City city = new City();
				city.setId(cursor.getInt(cursor.getColumnIndex("id")));
				city.setCityCode(cursor.getString(cursor.getColumnIndex("city_code")));
				city.setCityName(cursor.getString(cursor.getColumnIndex("city_name")));
				city.setProvinceId(provinceId);
				list.add(city);
			}while(cursor.moveToNext());
		}
		
		return list;
	}
	
	//将county实例存储到数据库
	public void saveCounty(County county){
		ContentValues values = new ContentValues();
		if(county != null){
			values.put("county_name", county.getCountyName());
			values.put("county_code", county.getCountyCode());
			values.put("city_id", county.getCityId());
			db.insert("County", null, values);
		}
	}
	
	//从数据库读取某市下所有县信息
	public List<County> loadCounties(int cityId){
		List<County> list = new ArrayList<County>();
		Cursor cursor = db.query("County",null,"city_id = ?", new String[]{String.valueOf(cityId)},null,null,null);
		if(cursor.moveToFirst()){
			do{
				County county = new County();
				county.setId(cursor.getInt(cursor.getColumnIndex("id")));
				county.setCountyCode(cursor.getString(cursor.getColumnIndex("county_code")));
				county.setCountyName(cursor.getString(cursor.getColumnIndex("county_name")));
				county.setCityId(cityId);
				list.add(county);
			}while(cursor.moveToNext());
		}
		return list;
	}
}
