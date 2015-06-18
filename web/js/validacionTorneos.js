/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 * Validaciones formularios de torneos
 * @autor: jj
 */

//FORMULARIO DE COPA
   $().ready(function(){
       $("#copa").validate({
	            rules:{
				  nombreTorneo:{
				    required: true,
					minlength: 3
				  },
				  capacidad:{
            		 required: true,
         			 maxlength: 4,
      			     number:true
        			  },
					  numgrupos:{
						  required:true
						  },
				 idaVueltagrupos:{
				  	required: true
				  },
          			idaVueltaeli:{
            		required: true,
          		  },
				  	finalIdaVuelta:{
					required: true
				  },
				  tercer:{
				  required: true,
        		},
				tipo:{
				  required: true,
					},
				inicio:{
					required: true,
					date: true
					},
				fin:{
					required: true,
					date:true
					}
		       },
		        messages:{
		      nombreTorneo:{
					required:"campo requerido",
				    minlength:"Minimo{0} caracteres"					  
				   },
             capacidad:{
					required:"campo requerido",
					maxlength:"maximo 4 digitos ",
					number:"Solo Numeros Por Favor"
		          },
				  numgrupos:{
					  required:"campo requerido"
					  },
			idaVueltagrupos:{
					required:"campo requerido"
					  },
        	idaVueltaeli:{
            		required:"campo requerido"
            			},
		    finalIdaVuelta:{
					  required:"campo requerido"  
					  },
        	tercer:{
            	    required:"campo requerido"
                	},
			tipo:{
					required:"campo requerido"
				},
			inicio:{
					required:"Indique la fecha de inicio de la liga",
					date:"Por favor ingrese una fecha válida"
			},
			fin:{
					required:"Indique la fecha de fin de la liga",
					date:"Por favor ingrese una fecha válida"
				}			  
		  }
	   
	       });
	   });

//FORMULARIO ELIMINATORIA
$().ready(function(){
       $("#eliminatoria").validate({
	            rules:{
				  nombreTorneo:{
				    required: true,
					minlength: 3
				  },
				  capacidad:{
            		 required: true,
         			 maxlength: 4,
      			     number:true
        			  },
				 grupos:{
				  	required: true
				  },
          			idaVuelta:{
            		required: true,
          		  },
				  	finalIdaVuelta:{
					required: true
				  },
				  tercer:{
				  required: true,
        		},
				 tipo:{
				  required:true,	 
					 },
				inicio:{
					required: true,
					date: true
					},
				fin:{
					required: true,
					date:true
					}
		       },
		        messages:{
		      nombreTorneo:{
					required:"Campo Requerido",
				    minlength:"Minimo{0} caracteres"					  
				   },
             capacidad:{
					required:"Camopo Requerido",
					maxlength:"maximo 4 digitos ",
					number:"Solo Numeros Por Favor"
		          },
			grupos:{
					required:"campo requerido"
					  },
        	idaVuelta:{
            		required:"campo requerido"
            			},
		    finalIdaVuelta:{
					  required:"campo requerido"  
					  },
        	tercer:{
            	    required:"campo requerido"
                	},
			tipo:{
					required:"campo requerido"
				},
			inicio:{
					required:"Indique la fecha de inicio de la liga",
					date:"Por favor ingrese una fecha válida"
			},
			fin:{
					required:"Indique la fecha de fin de la liga",
					date:"Por favor ingrese una fecha válida"
				}				  
		  }
	   
	       });
	   });
    
//FORMULARIO LIGA
$().ready(function(){
       $("#liga").validate({
	            rules:{
				    nombreTorneo:{
				    required: true,
					minlength: 3
				  },
				  capacidad:{
            		required: true,
         			maxlength: 4,
      			    number:true
        			  },
				 grupos:{
				  	required: true
				  },
          			idaVuelta:{
            		required: true,
          		  },
				  	finalIdaVuelta:{
					required: true
				  },
				  tercer:{
				  required: true
        		},
				tipo:{
					required: true
					},
				inicio:{
					required: true,
					date: true
					},
				fin:{
					required: true,
					date:true
					}
		  
		       },
		        messages:{
		      nombreTorneo:{
					required:"Campo Requerido",
				    minlength:"Minimo{0} caracteres"					  
				   },
             capacidad:{
					required:"Camopo Requerido",
					maxlength:"maximo 4 digitos ",
					number:"Solo Numeros Por Favor"
		          },
			grupos:{
					required:"campo requerido"
					  },
        	idaVuelta:{
            		required:"campo requerido"
            			},
		    finalIdaVuelta:{
					  required:"campo requerido"  
					  },
        	tercer:{
            	    required:"Campo Requerido"
                	},
			tipo:{
				    required:"Campo Requerido"
				},
			inicio:{
					required:"Indique la fecha de inicio de la liga",
					date:"Por favor ingrese una fecha válida"
			},
			fin:{
					required:"Indique la fecha de fin de la liga",
					date:"Por favor ingrese una fecha válida"
				}					  
		  }
	   
	       });
	   });
