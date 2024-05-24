/**
 * 
 */
$(document).ready(function() {
	// After load
	if (isMobile()) {
		toggleSidebar();
	}


	function toggleSidebar() {

		$('.cm-sidebar-wrapper').toggle();

		$('.cm-base-sidebar').toggleClass('collapsed');
		$('.cm-base-content-wrapper').toggleClass('expanded');

	}

	$('.cm-sidebar-toggle-btn').on('click', toggleSidebar);

	$(document).ready(function() {
		$('.cm-img input').on('change', function() {
			var input = this;

			if (input.files && input.files[0]) {
				var reader = new FileReader();

				reader.onload = function(e) {
					$(input).closest('.cm-img').find('img').attr('src', e.target.result);
				};

				reader.readAsDataURL(input.files[0]);
			}
		});
	});

});