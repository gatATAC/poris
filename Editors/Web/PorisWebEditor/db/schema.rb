# This file is auto-generated from the current state of the database. Instead of editing this file, 
# please use the migrations feature of Active Record to incrementally modify your database, and
# then regenerate this schema definition.
#
# Note that this schema.rb definition is the authoritative source for your database schema. If you need
# to create the application database on another system, you should be using db:schema:load, not running
# all the migrations from scratch. The latter is a flawed and unsustainable approach (the more migrations
# you'll amass, the slower it'll run and the greater likelihood for issues).
#
# It's strongly recommended to check this file into your version control system.

ActiveRecord::Schema.define(:version => 20160729080837) do

  create_table "labels", :force => true do |t|
    t.string   "name"
    t.integer  "node_id"
    t.integer  "scope_kind_id"
    t.datetime "created_at"
    t.datetime "updated_at"
  end

  add_index "labels", ["node_id"], :name => "index_labels_on_node_id"
  add_index "labels", ["scope_kind_id"], :name => "index_labels_on_scope_kind_id"

  create_table "node_attributes", :force => true do |t|
    t.string   "name"
    t.string   "content"
    t.datetime "created_at"
    t.datetime "updated_at"
    t.integer  "node_id"
    t.boolean  "visibility", :default => true
  end

  add_index "node_attributes", ["node_id"], :name => "index_node_attributes_on_node_id"

  create_table "node_types", :force => true do |t|
    t.string   "name"
    t.datetime "created_at"
    t.datetime "updated_at"
    t.string   "totype"
    t.string   "img_link"
  end

  create_table "nodes", :force => true do |t|
    t.string   "name"
    t.integer  "node_type_id"
    t.string   "type"
    t.datetime "created_at"
    t.datetime "updated_at"
    t.float    "rangemin"
    t.float    "rangemax"
    t.float    "default_float"
    t.string   "default_string"
    t.integer  "default_value_id"
    t.integer  "default_mode_id"
    t.integer  "project_id"
    t.integer  "parent_id"
    t.integer  "root_id"
    t.integer  "value_formatter_id"
    t.datetime "date_min"
    t.datetime "date_max"
    t.datetime "default_date"
    t.string   "file_extension"
    t.string   "file_description"
  end

  add_index "nodes", ["default_mode_id"], :name => "index_nodes_on_default_mode_id"
  add_index "nodes", ["default_value_id"], :name => "index_nodes_on_default_value_id"
  add_index "nodes", ["node_type_id"], :name => "index_nodes_on_node_type_id"
  add_index "nodes", ["parent_id"], :name => "index_nodes_on_parent_id"
  add_index "nodes", ["project_id"], :name => "index_nodes_on_project_id"
  add_index "nodes", ["root_id"], :name => "index_nodes_on_root_id"
  add_index "nodes", ["type"], :name => "index_nodes_on_type"
  add_index "nodes", ["value_formatter_id"], :name => "index_nodes_on_value_formatter_id"

  create_table "nodes_edges", :id => false, :force => true do |t|
    t.integer  "source_id"
    t.integer  "destination_id"
    t.datetime "updated_at"
    t.integer  "id"
    t.integer  "position"
    t.datetime "created_at"
  end

  add_index "nodes_edges", ["destination_id"], :name => "index_nodes_edges_on_destination_id"
  add_index "nodes_edges", ["source_id"], :name => "index_nodes_edges_on_source_id"

  create_table "project_memberships", :force => true do |t|
    t.datetime "created_at"
    t.datetime "updated_at"
    t.integer  "project_id"
    t.integer  "user_id"
    t.boolean  "contributor"
    t.integer  "role_id"
  end

  add_index "project_memberships", ["project_id"], :name => "index_project_memberships_on_project_id"
  add_index "project_memberships", ["role_id"], :name => "index_project_memberships_on_role_id"
  add_index "project_memberships", ["user_id"], :name => "index_project_memberships_on_user_id"

  create_table "projects", :force => true do |t|
    t.string   "name"
    t.datetime "created_at"
    t.datetime "updated_at"
    t.integer  "owner_id"
    t.string   "abbrev"
    t.string   "hostnameport", :default => "myhostname:5555"
  end

  add_index "projects", ["owner_id"], :name => "index_projects_on_owner_id"

  create_table "roles", :force => true do |t|
    t.string   "name"
    t.datetime "created_at"
    t.datetime "updated_at"
    t.text     "description"
    t.string   "abbrev"
  end

  create_table "scope_kinds", :force => true do |t|
    t.string   "name"
    t.text     "description"
    t.datetime "created_at"
    t.datetime "updated_at"
    t.string   "type"
    t.string   "src_format"
  end

  add_index "scope_kinds", ["type"], :name => "index_scope_kinds_on_type"

  create_table "users", :force => true do |t|
    t.string   "crypted_password",          :limit => 40
    t.string   "salt",                      :limit => 40
    t.string   "remember_token"
    t.datetime "remember_token_expires_at"
    t.string   "name"
    t.string   "email_address"
    t.boolean  "administrator",                           :default => false
    t.datetime "created_at"
    t.datetime "updated_at"
    t.string   "state",                                   :default => "active"
    t.datetime "key_timestamp"
    t.string   "abbrev"
  end

  add_index "users", ["state"], :name => "index_users_on_state"

  create_table "value_formatters", :force => true do |t|
    t.string   "name"
    t.datetime "created_at"
    t.datetime "updated_at"
  end

end
