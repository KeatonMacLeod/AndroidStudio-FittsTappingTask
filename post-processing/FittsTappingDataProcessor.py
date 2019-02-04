from ast import literal_eval
import numpy as np


def populate_data_dictionary():
    thumb_data = {}
    index_data = {}
    with open("all-data.txt", "r") as f:
        all_lines = f.readlines()
        for line in all_lines:
            result_dict = literal_eval(line)

            id = "A:{},W:{}".format(result_dict["amplitude"], result_dict["width"])
            device = result_dict["device"]
            result = result_dict["result"]

            if device == "thumb":
                if id not in thumb_data:
                    thumb_data[id] = {}
                    thumb_data[id]["success"] = []
                    thumb_data[id]["error"] = []
                if result == "success":
                    thumb_data[id]["success"].append(result_dict)
                elif result == "error":
                    thumb_data[id]["error"].append(result_dict)

            elif device == "index":
                if id not in index_data:
                    index_data[id] = {}
                    index_data[id]["success"] = []
                    index_data[id]["error"] = []
                if result == "success":
                    index_data[id]["success"].append(result_dict)
                elif result == "error":
                    index_data[id]["error"].append(result_dict)

    return {"thumb": thumb_data, "index": index_data}


def compute_average_movement_time_and_errors(all_data):
    # Compute the thumb's average movement times and error rates
    print("-" * 100)
    print("THUMB STATISTICS")
    thumb_id_indices = {}
    for id_combination, trials in all_data["thumb"].items():
        amplitude = trials["success"][0]["amplitude"]
        width = trials["success"][0]["width"]
        difficulty_index = np.log2((amplitude/width) + 1)
        movement_times = []
        for trial in trials["success"]:
            movement_times.append(trial["movement-time"])
        average_movement_time = "{0:.2f}".format(np.average(movement_times))

        error_percentage = 0
        if len(trials["error"]) != 0:
            error_percentage = "{0:.2f}".format(len(trials["error"]) / len(trials["success"]))

        if difficulty_index in thumb_id_indices:
            difficulty_index += .0001

        thumb_id_indices[difficulty_index] = {"COMBO": id_combination, "MT": average_movement_time, "ER": error_percentage}

    for key, value in sorted(thumb_id_indices.items()):
        print("{} \t\t COMBO:{}, MT:{}, ER:{}".format(key, value["COMBO"], value["MT"], value["ER"]))

    print("-" * 100)

    # Compute the index finger's average movement times and error rates
    print("INDEX STATISTICS")
    index_id_indices = {}
    for id_combination, trials in all_data["index"].items():
        amplitude = trials["success"][0]["amplitude"]
        width = trials["success"][0]["width"]
        difficulty_index = np.log2((amplitude/width) + 1)
        movement_times = []
        for trial in trials["success"]:
            movement_times.append(trial["movement-time"])
        average_movement_time = "{0:.2f}".format(np.average(movement_times))

        error_percentage = 0
        if len(trials["error"]) != 0:
            error_percentage = "{0:.2f}".format(len(trials["error"]) / len(trials["success"]))

        if difficulty_index in index_id_indices:
            difficulty_index += .0001

        index_id_indices[difficulty_index] = {"COMBO": id_combination, "MT": average_movement_time,
                                              "ER": error_percentage}

    for key, value in sorted(index_id_indices.items()):
        print("{} \t\t COMBO:{}, MT:{}, ER:{}".format(key, value["COMBO"], value["MT"], value["ER"]))

    print("-" * 100)


if __name__ == "__main__":
    id_combination_data = populate_data_dictionary()
    compute_average_movement_time_and_errors(id_combination_data)