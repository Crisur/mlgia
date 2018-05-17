from flask import Flask
import json
import requests
from datetime import datetime, date, time, timedelta
import calendar


app = Flask(__name__)


@app.route("/prediction/<id>/<actual_date>/<actual_time>", methods=['GET'])
def prediction(id, actual_date, actual_time):
	print("id: " + id)
	print("date: " + actual_date)
	print("time: " + actual_time)
	
	franja_horaria = calcula_franja(actual_time)
	day = day_week(actual_date)
	working_day= is_working_day(day)
	
	#TODO CALL TO MODEL PREDICTION CRISTINA
	
	#return "La Franja horaria es: "+franja_horaria+", dia de la semana:"+str(day)+", ¿es laborable ? "+str(working_day)
	return json.dumps({'predict':1})

def calcula_franja(actual_time):    
   
    horaMinSeg = actual_time.split(':')
    horaFinal = time(int(horaMinSeg[0]), int(horaMinSeg[1]), int(horaMinSeg[2]))
	
    print(horaFinal)
    # Recogemos la franja horaria en la que se encuentra nuestra hora
    hora1 = time(7, 0, 0)
    hora2 = time(10, 0, 0)
    hora3 = time(13, 0, 0)
    hora4 = time(16, 0, 0)
    hora5 = time(19, 0, 0)
    hora6 = time(22, 0, 0)
    hora7 = time(0, 0, 0)

    # De 7 a 10: 0
    # De 10 a 13: 1
    # De 13 a 16: 2
    # De 16 a 19: 3
    # De 19 a 22: 4
    # De 22 a 0: 5
    # De 00 a 7: 6

    if hora1 <= horaFinal< hora2:
        franjaHoraria = 0

    if hora2 <= horaFinal < hora3:
         franjaHoraria = 1
		
    if hora3 <= horaFinal < hora4:
        franjaHoraria = 2
		
    if hora4 <= horaFinal < hora5:
        franjaHoraria = 3
		
    if hora5 <= horaFinal < hora6:
        franjaHoraria = 4
		
    if hora6 <= horaFinal < hora7:
        franjaHoraria = 5
		
    if hora7 <= horaFinal < hora1:
        franjaHoraria = 6
		
    return str(franjaHoraria)
	
def day_week(actual_date):
	actual_date_array = actual_date.split('-')

	year = int(actual_date_array[0])
	month = int(actual_date_array[1])
	day = int(actual_date_array[2])

	final_date = date(year, month, day)
	##El metodo isoweekday nos devuelve el día de la semana
	day_week = datetime.isoweekday(final_date)
	## Se recoge el día de la semana que corresponde a la fecha (de 0 a 6)
	
	day_week = day_week - 1
	
	return day_week

def is_working_day(day_week):

	if day_week>4:
		is_workday=0
	else:
		is_workday=1

	
	return str(is_workday)

if __name__ == "__main__":
    app.run(port=8084, debug=True)
