class Hostnameport < ActiveRecord::Migration
  def self.up
    add_column :projects, :hostnameport, :string
  end

  def self.down
    remove_column :projects, :hostnameport
  end
end
