package uae.weather.myapplication.ui.list.models

data class City(val id:Long,val name:String){
    override fun toString(): String {
        return name
    }

    override fun equals(other: Any?): Boolean {
        if(other is City)
      return  id == other.id

        return false;
    }

    override fun hashCode(): Int {
        return id.hashCode()
    }

}