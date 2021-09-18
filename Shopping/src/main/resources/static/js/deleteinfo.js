$(document).ready(function() {
  $("tr #btn_item").click(function() {
	var idfav=$(this).parent().find("#item_id").val();
	
		 aryousure='Are you sure?';
		 deletefile='You will not be able to recover this imaginary file!';
		 yes='Yes, delete it!';
		 no='No, keep it';
		 sw_delete='Deleted!';
		 sw_msgd=' Your imaginary file is safe :)';
		 sw_cancel='Cancelled';
		 sw_msgs='Your file has been deleted.';
		 
	Swal.fire({
  title: aryousure,
  text: deletefile,
  icon: 'warning',
  showCancelButton: true,
  confirmButtonText: yes,
  cancelButtonText: no
}).then((result) => {
  if (result.value) {
	    deletefav(idfav);
    Swal.fire(
      sw_delete,
      sw_msgd,
      'success'
    ).then((result) =>{
	if (result.value){
		parent.location.href="/cart";
  }
});

  } else if (result.dismiss === Swal.DismissReason.cancel) {
    Swal.fire(
      sw_cancel,
      sw_msgs,
      'error'
    )
  }
});

   });
   function deletefav(idfav){
	var url="/cart/remove/";
	           $.ajax({
		                type:'GET',
		                url: url+idfav,
		                
                        async:false,
                        cache:false,
                        success: function(data, textStatus, jqXHR) {

                        },
                        error: function(e) {
                        	Swal.fire('Oops...', 'Something went wrong!', 'error');
                            
                        }
                    });
}
   
});

$("tr #qty").click(function(){
	
	var idp=$(this).parent().find("#pid").val();
	var qty=$(this).parent().find("#qty").val();
	//alert('test/'+idp+'/'+qty);
	var url="/cart/buy/"+idp+"/"+qty;
	
	$.ajax({
		 type:"GET",
		 url:url,
         async:false,
         cache:false,
	   	 success: function(data,textStatus,jqXHR){
			location.href="/cart/";
		}, error: function(e) {
              Swal.fire('Oops...', 'Something went wrong!', 'error');
             }
	});
});
