terraform {

  backend "remote" {
    hostname = "app.terraform.io"
    organization = "canelhas"

    workspaces {
      name = "cars"
    }
  }

  required_providers {
    aws = {
      source = "hashicorp/aws"
      version = "~> 3.40"

    }
  }
}

#region providers
provider "aws" {
  profile = "cars"
  region = "us-east-1"
}

#endregion

locals {

  instance_type = "t3a.micro"
  url = "cars.canelhas.io"
  environment_name = "cars-api"
  application_name = "cars-prod"
  registry_name = "cars"
  certificate_arn = "arn:aws:acm:us-east-1:729285284372:certificate/1e7cb229-a84b-4391-90d8-12ad05e250bb"
  zone_id = "Z0888034268Y5H2AZ7G9U"
}


module "cars-api" {
  source = "./modules/compose_beanstalk"


  zone_id = local.zone_id
  access_url = local.url
  certificate_arn = local.certificate_arn


  environment_name = local.environment_name
  application_name = local.application_name
  instance_type = local.instance_type


}

resource "aws_ecr_repository" "registry" {
  name = local.registry_name
}