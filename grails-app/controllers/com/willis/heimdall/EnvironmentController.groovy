package com.willis.heimdall

class EnvironmentController {

    def scaffold = true
	
	def calendar = {
		def environment = Environment.findById(params.id)
		[ environment : environment ]
	}
}
