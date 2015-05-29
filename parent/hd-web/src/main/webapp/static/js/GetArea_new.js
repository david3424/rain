
function GetProvinceName(provinceValue){
	if(isNaN(provinceValue))
		return "";
		
	return Province[(provinceValue-10)*2 + 1];
}

function GetCityName(provinceValue, cityValue){
	
if(isNaN(provinceValue) || isNaN(cityValue))
	return "";
	
	var firstCityId = provinceValue + "01";
	
	
	return ( City[provinceValue][2*(cityValue - firstCityId) + 1]);
	
}

function GetProvince(strTheForm,strProvince,strCity,strCounty,DefaultID)
{
	strProvince = strTheForm + "." + strProvince
	strCity = strTheForm + "." + strCity
	strCounty = strTheForm + "." + strCounty
	var TheProvince = eval(strProvince)
	var TheCity = eval(strCity)
	var TheCounty = eval(strCounty)
	TheCity.disabled = true
	TheCounty.disabled = true
    if(Province.length != 0)
    {
        TheProvince.options.length = 0
        TheProvince.options[0] = new Option('省份','')
        TheProvince.options[0].selected = true
        var j = 1
        for(i=0;i<Province.length;i+=2)
        {
            if(Province[i] == "" || Province[i+1] == "")
            {
            	continue
            }
        	else
        	{
        		TheProvince.options[j] = new Option(Province[i+1],Province[i])
        		j++
        	}
        }
    }
    if(DefaultID != "")
    {
    	DefaultID = DefaultID.toString()
    	var DefaultProvince = DefaultID.substring(0,2)
    	var DefaultCity = DefaultID.substring(0,4)
    	var DefaultCounty = DefaultID.substring(0,6)
    	GetDefaultItem(TheProvince,DefaultProvince)
    	GetCity(strTheForm,strProvince,strCity,strCounty,DefaultID)
    	GetDefaultItem(TheCity,DefaultCity)
    	GetCounty(strTheForm,strProvince,strCity,strCounty,DefaultID)
    	GetDefaultItem(TheCounty,DefaultCounty)
    }
}

function GetCity(strTheForm,strProvince,strCity,strCounty,DefaultID)
{
	if(DefaultID == ""){
	strProvince = strTheForm + "." + strProvince
	strCity = strTheForm + "." + strCity
	strCounty = strTheForm + "." + strCounty
	}
	var TheProvince = eval(strProvince)
	var TheCity = eval(strCity)
	var TheCounty = eval(strCounty)
	
	if(TheProvince.selectedIndex != 0)
	{
		TheCity.options.length = 0
		TheCity.options[0] = new Option('城市','')
		TheCity.options[0].selected = true
		TheCity.disabled = true
		TheCounty.options.length = 0
		TheCounty.options[0] = new Option('地区','')
		TheCounty.options[0].selected = true
		TheCounty.disabled = true
		CityIndex = TheProvince.options[TheProvince.selectedIndex].value
		var j = 1
   for(i=0;i<City[CityIndex].length;i+=2)
        {
     if(City[CityIndex][i] == "" || City[CityIndex][i+1] == "")
            {
            	continue
            }
    else
        	{
            	TheCity.options[j] = new Option(City[CityIndex][i+1],City[CityIndex][i])
            	j++
            }
        }
   if(TheCity.options.length > 1) TheCity.disabled = false
	}
	else
	{
		TheCity.options.length = 0
		TheCity.options[0] = new Option('城市','')
		TheCity.options[0].selected = true
		TheCity.disabled = true
		TheCounty.options.length = 0
		TheCounty.options[0] = new Option('地区','')
		TheCounty.options[0].selected = true
		TheCounty.disabled = true
	}
}

function GetCounty(strTheForm,strProvince,strCity,strCounty,DefaultID)
{
	if(DefaultID == ""){
	strProvince = strTheForm + "." + strProvince
	strCity = strTheForm + "." + strCity
	strCounty = strTheForm + "." + strCounty
	}
	var TheProvince = eval(strProvince)
	var TheCity = eval(strCity)
	var TheCounty = eval(strCounty)
	if(TheProvince.selectedIndex != 0 && TheCity.selectedIndex != 0)
	{
		TheCounty.options.length = 0
		TheCounty.options[0] = new Option('地区','')
		TheCounty.options[0].selected = true
		TheCounty.disabled = true
		CountyIndex = TheCity.options[TheCity.selectedIndex].value
		var j = 1
        for(i=0;i<County[CountyIndex].length;i+=2)
        {
            if(County[CountyIndex][i] == "" || County[CountyIndex][i+1] == "")
            {
            	continue
            }
        	else
            {
            	TheCounty.options[j] = new Option(County[CountyIndex][i+1],County[CountyIndex][i])
            	j++
            }
        }
        if(TheCounty.options.length > 1) TheCounty.disabled = false
	}
	else
	{
		TheCounty.options.length = 0
		TheCounty.options[0] = new Option('地区','')
		TheCounty.options[0].selected = true
		TheCounty.disabled = true
	}
}

function GetDefaultItem(obj,objval)
{
	for(i=0;i<obj.length;i++)
	{
		if (obj.options[i].value==objval)
		{
			obj.disabled = false
			obj.options[i].selected=true
			break;
		}
	}
}

