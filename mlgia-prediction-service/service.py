from flask import Flask
import json
from flask import request
from datetime import datetime, date, time, timedelta
import calendar

app = Flask(__name__)


@app.route("/prediction", methods=['POST'])
def prediction():
    '''
    Calculate the prediction to park in a parking (id), in a especific hour (time) in a specific day (date)
    Parameters:
    id : The id of the parking
    date : The day to do the prediction in format 'yyyy-dd-mm'
    time : The hour to do the prediction in format 'hh:mm:ss'
    :return: int
    '''
    data = json.loads(request.data)

    id = data['parkingId']
    actual_date = data['date']
    actual_time = data['time']

    time_zone = calculate_time_zone(actual_time)
    day = day_week(actual_date)
    working_day = is_working_day(day)

    # TODO CALL TO MODEL PREDICTION CRISTINA

    return "La Franja horaria es: "+str(time_zone)+", dia de la semana:"+str(day)+", ¿es laborable ? "+str(working_day)
    #return json.dumps({'predict': 1})


def calculate_time_zone(actual_time):
    '''
    Calculate the time_zone of the hour.
    # From 7 to 10: time_zone = 0
    # From 10 to 13: time_zone = 1
    # From 13 to 16: time_zone = 2
    # From 16 to 19: time_zone = 3
    # From 19 to 22: time_zone = 4
    # From 22 to 0: time_zone = 5
    # From 00 to 7: time_zone = 6
    :param actual_time:
    :return:
    '''


    actual_time_array=actual_time.split(':')
    actual_hour = time(int(actual_time_array[0]), int(actual_time_array[1]), int(actual_time_array[2]))
    time_zone = None

    #Declae our time_zones
    time_zone_1 = time(7, 0, 0)
    time_zone_2 = time(10, 0, 0)
    time_zone_3 = time(13, 0, 0)
    time_zone_4 = time(16, 0, 0)
    time_zone_5 = time(19, 0, 0)
    time_zone_6 = time(22, 0, 0)
    time_zone_7 = time(0, 0, 0)

    if time_zone_1 <= actual_hour < time_zone_2:
        time_zone = 0

    if time_zone_2 <= actual_hour < time_zone_3:
        time_zone = 1

    if time_zone_3 <= actual_hour < time_zone_4:
        time_zone = 2

    if time_zone_4 <= actual_hour < time_zone_5:
        time_zone = 3

    if time_zone_5 <= actual_hour < time_zone_6:
        time_zone = 4

    if time_zone_6 <= actual_hour < time_zone_7:
        time_zone = 5

    if time_zone_7 <= actual_hour < time_zone_1:
        time_zone = 6

    return time_zone


def day_week(actual_date):
    '''
    Return the day of week of the date
    :param actual_date:
    :return:
    '''

    actual_date_array = actual_date.split('-')

    year = int(actual_date_array[0])
    month = int(actual_date_array[1])
    day = int(actual_date_array[2])

    final_date = date(year, month, day)
    day_week = datetime.isoweekday(final_date)

    #Day_week from 0 to 6
    day_week = day_week - 1

    return day_week


def is_working_day(day_week):
    '''
    Return if the day is a working day or not
    :param day_week:
    :return:
    '''
    if day_week > 4:
        is_workday = 0
    else:
        is_workday = 1

    return is_workday


if __name__ == "__main__":
    app.run(port=8084, debug=True)
