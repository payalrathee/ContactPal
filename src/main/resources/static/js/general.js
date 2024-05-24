/**
 * 
 */
function isMobile() {
	return ( ( window.innerWidth <= 800 ));
}

function deleteContact(id) {
	
	Swal.fire({
	  title: "Are you sure?",
	  text: "You won't be able to revert this!",
	  icon: "warning",
	  showCancelButton: true,
	  confirmButtonColor: "#3085d6",
	  cancelButtonColor: "#d33",
	  confirmButtonText: "Yes, delete it!"
	}).then((result) => {
	  if (result.isConfirmed) {
	    window.location = "/contact/delete/" + id;
	  }
	});
}

function deleteUser() {
	
	Swal.fire({
	  title: "Are you sure?",
	  text: "You won't be able to revert this!",
	  icon: "warning",
	  showCancelButton: true,
	  confirmButtonColor: "#3085d6",
	  cancelButtonColor: "#d33",
	  confirmButtonText: "Yes, delete it!"
	}).then((result) => {
	  if (result.isConfirmed) {
	    window.location = "/user/delete";
	  }
	});
}