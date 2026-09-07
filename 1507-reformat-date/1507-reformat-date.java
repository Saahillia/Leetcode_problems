class Solution {
    public String reformatDate(String date) {
        String months[] = {"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"};
        String[] parts = date.split(" ");
        String dayPart = parts[0];
        String monthPart = parts[1];
        String yearPart = parts[2];

        int monthIndex = 0;
        for(int i=0; i<months.length; i++){
            if(months[i].equals(monthPart)){
                monthIndex = i+1;
                break;
            }
        }
        String month = String.format("%02d", monthIndex);

        String dayDigits = dayPart.substring(0, dayPart.length() -2);
        String day = String.format("%02d", Integer.parseInt(dayDigits));

        return yearPart +"-" + month + "-" + day;

    }
}