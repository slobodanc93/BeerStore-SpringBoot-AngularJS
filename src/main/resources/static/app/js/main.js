var knjizaraApp = angular.module("knjizaraApp", ['ngRoute']);

knjizaraApp.config(['$routeProvider', function ($routeProvider) {
	$routeProvider
	.when('/', {
		templateUrl : '/app/html/home.html',
	})
	.when('/piva', {
		templateUrl : '/app/html/piva.html'
    }).when('/pivo/edit/:id',{
        templateUrl: '/app/html/edit-pivo.html'
    }).otherwise({
        redirectTo: '/'
    });
}]);

knjizaraApp.controller("homeCtrl", function($scope){
	$scope.message = "Dobro došli u aplikaciju za vođenje evidencije prodavnice piva";
});

knjizaraApp.controller("pivaCtrl", function($scope, $http, $location){
	
	/*** INICIJALIZACIJA ***/
	var baseUrlPivare = "/api/pivare";
	var baseUrlPiva = "api/piva";
	
	$scope.pageNum = 0;
	$scope.totalPages = 0;
	
	$scope.piva = [];
	$scope.pivare = [];
	
	$scope.formAdd = {};
	
	$scope.formSearch = {};
	$scope.formSearch.nazivPiva = "";
	$scope.formSearch.minIbu = "";
	$scope.formSearch.maxIbu = "";
	$scope.formSearch.nazivPivare = "";
	var nestaloPiva = false;
	
	/*** ALERTS ***/
    $scope.showDangerAlert = false;
    $scope.showSuccessAlert = false;
    $scope.alertMessage = "";
    $scope.close = function(){
    	$scope.showDangerAlert = false;
    	$scope.showSuccessAlert = false;
    }


	/*** FUNKCIJA ZA DOBAVLJANJE KNJIGA ***/
	var getPiva = function() {
		
		var config = {params: {}};
		config.params.pageNum = $scope.pageNum;
		
		if($scope.formSearch.nazivPiva != "") {
			config.params.nazivPiva = $scope.formSearch.nazivPiva;
		}
		
		if($scope.formSearch.minIbu != "") {
			config.params.minIbu = $scope.formSearch.minIbu;
		}
		
		if($scope.formSearch.maxIbu != "") {
			config.params.maxIbu = $scope.formSearch.maxIbu;
		}
		
		if($scope.formSearch.nazivPivare != "") {
			config.params.nazivPivare = $scope.formSearch.nazivPivare;
		}
		
		if(nestaloPiva) {
			config.params.nestalo = 0;
		}
		
		$http.get(baseUrlPiva, config)
			.then(
				function success(res) {
					$scope.piva = res.data;
					$scope.totalPages = res.headers('totalPages');
				},
				function error(res) {
					alert("Neuspešno dobavljanje piva");
				}
			);
		
	}
	
	
	/*** FUNKCIJA ZA DOBAVLJANJE IZDAVACA ***/
	var getPivare = function() {	
		$http.get(baseUrlPivare)
			.then(
				function success(res) {
					$scope.pivare = res.data;
				},
				function error(res) {
					alert("Neuspešno dobavljanje pivara");
				}	
			);	
	}
	
	
	/*** POZIVAMO DOBAVLJANJE PODATAKA ***/
	getPivare();
	getPiva();
	
	
	/*** NAVIGACIJA TABELE ***/
	$scope.nazad = function(){
        if($scope.pageNum > 0) {
            $scope.pageNum = $scope.pageNum - 1;
            getPiva();
        }
    }

    $scope.napred = function(){
        if($scope.pageNum < $scope.totalPages - 1){
            $scope.pageNum = $scope.pageNum + 1;
            getPiva();
        }
    }
	
	
	/*** DODAVANJE NOVOG PIVA ***/
	$scope.add = function() {
		$http.post(baseUrlPiva, $scope.formAdd)
			.then(
				function success(res){
					getPiva();
					$scope.formAdd = {};
					$scope.alertMessage = "Pivo je uspešno dodato"
					$scope.showDangerAlert = false;
					$scope.showSuccessAlert = true;
				},
				function error(res){
					$scope.alertMessage = "Proverite unete podatke, pivo nije uspešno dodato"
					$scope.showSuccessAlert = false;
					$scope.showDangerAlert = true;
				}		
			);
	}
	
	
	/*** PRETRAGA ***/
	$scope.search = function() {
		$scope.pageNum = 0;
		getPiva();
	}
	
	$scope.resetSearch = function() {
		$scope.pageNum = 0;
		$scope.formSearch = {};
		getPiva();
	}
	
	
	$scope.nestalo = function() {
		nestaloPiva = true;
		getPiva();
	}
	
	$scope.resetNestalo = function() {
		$scope.pageNum = 0;
		nestaloPiva = false;
		getPiva();
	}
	
	
	/*** IZMENA ***/
	$scope.edit = function(id) {
		$location.path("/pivo/edit/" + id);
	}
	
	/*** IZMENA NA GLAVNOJ STRANI ***/
	$scope.editMainPage = function(id) {
		
		$scope.fromAdd = {};
		$scope.showIzmeniButton = true;
		
		var getPivoForEdit = function() {
			$http.get(baseUrlPiva + "/" + id)
		        .then(
		        	function success(res){
		        		$scope.formAdd = res.data;
		        	},
		        	function error(data){
		        		alert("Neuspešno dobavljanje piva");
		        	}
		        );
		}
			
		var getPivare = function(){
			    $http.get(baseUrlPivare)
			        .then(
			        	function success(res){
			        		$scope.pivare = res.data;
			        		getPivoForEdit();
			        	},
			        	function error(res){
			        		alert("Neuspešno dobavljanje pivara");
			        	}
			        );
		}
			
		getPivare();
		
		$scope.izmeni = function() {
			$http.put(baseUrlPiva + "/" + $scope.formAdd.id, $scope.formAdd)
		        .then(
		    		function success(data){
						$scope.alertMessage = "Uspešno izmenjeno pivo";
						$scope.showSuccessAlert = true;
						$scope.showDangerAlert = false;
		    			getPiva();
		    			$scope.showIzmeniButton = false;
		    			$scope.formAdd = {};
		    		},
		    		function error(data){
						$scope.alertMessage = "Proverite unete podatke, pivo nije uspešno izmenjeno";
						$scope.showSuccessAlert = false;
						$scope.showDangerAlert = true;
		    		}
		        );
		}
	}
	
	

	
	
	
	/*** BRISANJE ***/
	$scope.delete = function(id) {
		$http.delete(baseUrlPiva + "/" + id)
			.then(
				function success(res){
					getPiva();
					$scope.alertMessage = "Brisanje uspešno izvršeno";
					$scope.showDangerAlert = false;
					$scope.showSuccessAlert = true;
				},
				function error(res){
					$scope.alertMessage = "Došlo je do greške, brisanje nije uspešno izvršeno";
					$scope.showDangerAlert = true;
					$scope.showSuccessAlert = false;
				}
			);
	}
	
	
	/*** KUPOVINA ***/
    $scope.kupi = function(id){
    	$http.post(baseUrlPiva + "/" + id + "/kupovina")
    		.then(
				function success(data){
					getPiva();
					$scope.alertMessage = "Kupovina uspešno izvršena";
					$scope.showDangerAlert = false;
					$scope.showSuccessAlert = true;
				},
				function error(data){
					$scope.alertMessage = "Nije moguće izvršiti kupovinu";
					$scope.showDangerAlert = true;
					$scope.showSuccessAlert = false;
				}
    	);
    }
 
	
});

knjizaraApp.controller("editPivoCtrl", function($scope, $http, $routeParams, $location){

	/*** INICIJALIZACIJA ***/
	var baseUrlPivare = "/api/pivare";
	var baseUrlPiva = "api/piva";
	
	$scope.formEdit = {};
	
	/*** FUNKCIJA ZA DOBAVLJANJE PIVA ZA IZMENU ***/
	var getPivoForEdit = function() {
		$http.get(baseUrlPiva + "/" + $routeParams.id)
	        .then(
	        	function success(res){
	        		$scope.formEdit = res.data;
	        	},
	        	function error(data){
	        		alert("Neuspešno dobavljanje piva");
	        	}
	        );
	}
	
	
	/*** FUNKCIJA ZA DOBAVLJANJE PIVARE ***/
	var getPivare = function(){
		    $http.get(baseUrlPivare)
		        .then(
		        	function success(res){
		        		$scope.pivare = res.data;
		        		getPivoForEdit();
		        	},
		        	function error(res){
		        		alert("Neuspešno dobavljanje pivara");
		        	}
		        );
	}
	
	
	/*** POZIVAMO DOBAVLJANJE PODATAKA ***/
	getPivare();
	
	
	/*** FUNKCIJA ZA IZMENU PIVA ***/
	$scope.izmeni = function() {
		$http.put(baseUrlPiva + "/" + $scope.formEdit.id, $scope.formEdit)
	        .then(
	    		function success(data){
	    			alert("Uspešno izmenjeno pivo");
	    			$location.path("/piva");
	    		},
	    		function error(data){
	    			$scope.formSubmitted = true;
	    			alert("Neuspešna izmena piva");
	    		}
	        );
	}


});

